<?php

namespace backend\controllers;

use common\models\Reserva;
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
                            'actions' => ['index','view','update','delete','quartos-disponiveis'],
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
        $this->findModel($id)->delete();

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
