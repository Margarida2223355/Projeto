<?php

namespace frontend\controllers;

use common\models\Reserva;
use common\models\Quarto;
use DateTime;
use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;


/**
 * ReservaController implements the CRUD actions for Reserva model.
 */
class ReservaController extends Controller
{
    /**
     * @inheritDoc
     */
    public function behaviors()
    {
        return array_merge(
            parent::behaviors(),
            [
                'access' => [
                    'class' => AccessControl::class,
                    'rules' => [
                        [
                            'actions' => ['create','index','quartos-disponiveis'],
                            'allow' => true,
                            'roles' => ['@'],
                        ],
                        [
                            'actions' => ['update','delete','view'],
                            'allow' => true,
                            'roles' => ['@'],
                        ],
                        // [
                        //     'actions' => ['update','delete','view'],
                        //     'allow' => true,
                        //     'roles' => ['@'],
                        //     'matchCallback' => function ($rule, $action) {
                        //         // Verificar se o user está tentando acessar ou modificar sua própria reserva
                        //         $userId = Yii::$app->user->id;
                        //         $reservaId = Yii::$app->request->getQueryParam('id');
                        //         $reserva = Reserva::findOne($reservaId);
                                
                        //         if($reserva == null){
                        //             return false;
                        //         }
                        //         return $userId == $reserva->cliente_id;
                        //     }
                        // ],
                    ],
                ],
                'verbs' => [
                    'class' => VerbFilter::className(),
                    'actions' => [
                        'delete' => ['POST'],
                    ],
                ],
            ]
        );
    }

    /**
     * Lists all Reserva models.
     *
     * @return string
     */
    public function actionIndex()
    {
        $userId = Yii::$app->user->id;
        $model = new Reserva();
        $dataProvider = new ActiveDataProvider([
            'query' => Reserva::find()->where(['cliente_id'=>$userId]),
            /*
            'pagination' => [
                'pageSize' => 50
            ],
            'sort' => [
                'defaultOrder' => [
                    'id' => SORT_DESC,
                ]
            ],
            */
        ]);

        return $this->render('index', [
            'dataProvider' => $dataProvider,
            'model' =>$model,
        ]);
    }

    /**
     * Displays a single Reserva model.
     * @param int $id ID
     * @return string
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        $reserva = $this->findModel($id);
        
        if (\Yii::$app->user->can('permission_self_operations', ['reserva' => $reserva])) {
            // update post
            return $this->render('view', [
                'model' => $reserva,
            ]);
        }
        else{
            throw new \yii\web\ForbiddenHttpException('Você não tem permissão para acessar esta página.');
        }
        
    }

    public function actionQuartosDisponiveis($dataInicial, $dataFinal){
       
        //verifica se as datas estao vazias
        if (!empty($dataInicial) && !empty($dataFinal)) {
            $listaQuartosDisponiveis = Quarto::quartosDisponiveis($dataInicial, $dataFinal);
            return $this->render('quartos', [
                'quartos' => $listaQuartosDisponiveis,
                'dataInicial'=>$dataInicial,
                'dataFinal'=>$dataFinal,
            ]);
        } else {
            Yii::$app->session->setFlash('error', 'Por favor, as datas que deseja reservar.');
            throw new NotFoundHttpException('The requested page does not exist.');
        }
        
    }
    /**
     * Creates a new Reserva model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate($quarto_id,$dataInicial,$dataFinal)
    {
        $quartos = Quarto::find()->all();

        $model = new Reserva();
        $model->cliente_id = Yii::$app->user->id;
        $model->quarto_id = $quarto_id;
        $model->data_inicial = $dataInicial;
        $model->data_final = $dataFinal;
        $model->status = "carrinho";

        // Verificar se ja existe uma reserva para o mesmo quarto com datas sobrepostas
        $reservaSobreposta = Reserva::find()
        ->andWhere(['quarto_id' => $quarto_id])
        ->andWhere(['or',
            ['and', ['>=', 'data_inicial', $dataInicial], ['<=', 'data_inicial', $dataFinal]],
            ['and', ['>=', 'data_final', $dataInicial], ['<=', 'data_final', $dataFinal]],
            ['and', ['<=', 'data_inicial', $dataInicial], ['>=', 'data_final', $dataFinal]],
        ])
        ->one();

        if ($reservaSobreposta) {
            Yii::$app->session->setFlash('error', 'Já existe uma reserva para este quarto nesse período.');
            return $this->redirect(['index']);
        }

        // Converta as strings de data para objetos DateTime
        $dataInicialDT = new DateTime($dataInicial);
        $dataFinalDT = new DateTime($dataFinal);
        // Calcule a diferença entre as duas datas
        $diferenca = $dataInicialDT->diff($dataFinalDT);
        // Acesse o número de dias a partir do objeto DateInterval
        $numDias = $diferenca->days;

        $model->preco_total = $numDias * $model->quarto->preco;



        if ($this->request->isPost) {
            if ($model->load($this->request->post()) && $model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }
        } else {
            $model->loadDefaultValues();
        }

        return $this->render('create', [
            'model' => $model,
            'quartos'=>$quartos,
        ]);
    }

    /**
     * Updates an existing Reserva model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return string|\yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);
        //$quartos = Quarto::find()->all();

        if (\Yii::$app->user->can('permission_self_operations', ['reserva' => $model])) {
            if ($this->request->isPost && $model->load($this->request->post())) {
                // Verificar se ja existe uma reserva para o mesmo quarto com datas sobrepostas
                $reservaSobreposta = Reserva::find()
                ->andWhere(['quarto_id' => $model->quarto_id])
                ->andWhere(['or',
                    ['and', ['>=', 'data_inicial', $model->data_inicial], ['<=', 'data_inicial', $model->data_final]],
                    ['and', ['>=', 'data_final', $model->data_inicial], ['<=', 'data_final', $model->data_final]],
                    ['and', ['<=', 'data_inicial', $model->data_inicial], ['>=', 'data_final', $model->data_final]],
                ])
                ->one();

                if ($reservaSobreposta) {
                    Yii::$app->session->setFlash('error', 'Já existe uma reserva para este quarto nesse período.');
                    return $this->redirect(['index']);
                }
                $model->save();
                return $this->redirect(['view', 'id' => $model->id]);
            }

            return $this->render('update', [
                'model' => $model,
            ]);
        }
        else{
            throw new \yii\web\ForbiddenHttpException('Você não tem permissão para acessar esta página.');
        }

    }

    /**
     * Deletes an existing Reserva model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param int $id ID
     * @return \yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        $model = $this->findModel($id);
        if (\Yii::$app->user->can('permission_self_operations', ['reserva' => $model])) {
            
            // alterar a reserva para cancelada

        }
        else{
            throw new \yii\web\ForbiddenHttpException('Você não tem permissão para acessar esta página.');
        }
        return $this->redirect(['index']);
    }

    /**
     * Finds the Reserva model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Reserva the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Reserva::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
