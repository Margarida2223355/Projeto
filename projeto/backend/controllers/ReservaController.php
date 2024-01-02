<?php

namespace backend\controllers;

use common\models\Fatura;
use common\models\InfUser;
use common\models\LinhaFatura;
use common\models\Quarto;
use common\models\Reserva;
use DateTime;
use Yii;
use yii\data\ActiveDataProvider;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

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
                            'actions' => ['index','view','create','update','delete','ativar','quartos-disponiveis'],
                            'allow' => true,
                            'roles' => ['acederBackend'],
                        ],
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
        $model = new Reserva();
        $dataProvider = new ActiveDataProvider([
            'query' => Reserva::find(),
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
        return $this->render('view', [
            'model' => $this->findModel($id),
        ]);
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
    public function actionCreate($quarto_id,$dataInicial,$dataFinal)
    {
        $quartos = Quarto::find()->all();
        $clientes = InfUser::find()->all();

        $model = new Reserva();
        $model->quarto_id = $quarto_id;
        $model->data_inicial = $dataInicial;
        $model->data_final = $dataFinal;
        $model->status = "inativa";

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

        $model->preco_total = ($numDias < 1) ? $model->quarto->preco : $numDias * $model->quarto->preco;

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
            'clientes'=>$clientes,
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

        // Verificar se o quarto_id do modelo é igual ao quarto_id recebido via POST
        if ($this->request->isPost) {
            $postedQuartoId = $this->request->post('Reserva')['quarto_id'];

            if ($model->quarto_id != $postedQuartoId) {
                $model->load($this->request->post());
                // Verificar se ja existe uma reserva para o mesmo quarto com datas sobrepostas
                $reservaSobreposta = Reserva::find()
                ->andWhere(['quarto_id' => $postedQuartoId])
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
        }

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
        if (Fatura::faturaExistsForReservaId($id)) {
            Yii::$app->session->setFlash('error', 'Essa reserva não pode ser apagada pois já existe uma fatura.');
            return $this->redirect(['index']);
        }
        if (LinhaFatura::verificarPedidosConfirmados($id)) {
            // Caso em que já existe uma LinhaFatura confirmada na reserva
            Yii::$app->session->setFlash('error', 'Essa reserva não pode ser apagada pois já existe um pedido confirmado para esta reserva.');
            return $this->redirect(['index']);
        }
        // Deleta todas as LinhaFatura associadas à reserva
        LinhaFatura::deleteAll(['reserva_id' => $id]);
        $model->delete();

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
    public function actionAtivar($id)
    {
        $model = $this->findModel($id);
        if(Fatura::findOne(['reserva_id'=>$id])){
            Yii::$app->session->setFlash('error', 'Essa reserva não pode ser ativada pois já foi encerrada.');
            return $this->redirect(['index']);
        }
        $model->status = 'Ativa';
        $model->save();

        return $this->redirect(['index']);
    }
}
