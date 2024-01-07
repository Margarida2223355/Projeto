<?php

namespace frontend\controllers;

use common\models\Fatura;
use common\models\Reserva;
use DateTime;
use Yii;
use yii\data\ActiveDataProvider;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * FaturaController implements the CRUD actions for Fatura model.
 */
class FaturaController extends Controller
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
                    'only' => ['view','create','index'],
                    'rules' => [
                        [
                            'actions' => ['view'],
                            'allow' => true,
                            'roles' => ['@'],
                            'matchCallback' => function ($rule, $action) {
                                // Verificar se o user está tentando acessar ou modificar sua própria view
                                $userId = Yii::$app->user->id;
                                $faturaId = Yii::$app->request->getQueryParam('id');
                                
                                if($faturaId == null){
                                    return false;
                                }
                                $fatura = Fatura::findOne($faturaId);
                                return $fatura !== null && $userId == $fatura->reserva->cliente_id;
                            }
                        ],
                        [
                            'actions' => ['index'],
                            'allow' => true,
                            'roles' => ['@'],
                            'matchCallback' => function ($rule, $action) {
                                $userId = Yii::$app->user->id;
                                $requestedUserId = Yii::$app->request->getQueryParam('userId');
                                return $userId == $requestedUserId;
                            }
                        ],
                        [
                            'actions' => ['create'],
                            'allow' => true,
                            'roles' => ['@'],
                            'matchCallback' => function ($rule, $action) {
                                $userId = Yii::$app->user->id;
                                $reservaId = Yii::$app->request->getQueryParam('id');
                                $reserva = Reserva::find()->where(['id'=>$reservaId])->one();
                                return $userId == $reserva->cliente_id;
                            }
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
     * Lists all Fatura models.
     *
     * @return string
     */
    public function actionIndex($userId)
    {

        $dataProvider = new ActiveDataProvider([
            'query' => Fatura::find()->joinWith('reserva')->andWhere(['reserva.cliente_id' => $userId]),
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
        ]);
    }

    /**
     * Displays a single Fatura model.
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

    /**
     * Creates a new Fatura model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate($id)
    {
        if (Fatura::faturaExistsForReservaId($id)) {
            // Caso já exista uma fatura para esta reserva
            Yii::$app->session->setFlash('error', 'Já existe uma fatura para esta reserva.');
            return $this->redirect(['reserva/index']);
        }

        $model = new Fatura();
        $model->reserva_id = $id;
        $model->preco_total = $model->reserva->calcularTotal();
        $model->data_pagamento = date('Y-m-d');
        $model->pousada_id = 1;

        //Quando cria a fatura a reserva fica inativa
        $reserva = Reserva::findOne(['id'=>$model->reserva_id]);
        $reserva->status = "Inativa";
        $reserva->save();

        if ($this->request->isPost) {
            if ($model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }
        } else {
            $model->loadDefaultValues();
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Finds the Fatura model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return Fatura the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Fatura::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
    
}
