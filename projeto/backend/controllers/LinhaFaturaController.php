<?php

namespace backend\controllers;

use common\models\Fatura;
use common\models\LinhaFatura;
use common\models\Refeicao;
use common\models\Servico;
use Yii;
use yii\data\ActiveDataProvider;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * LinhaFaturaController implements the CRUD actions for LinhaFatura model.
 */
class LinhaFaturaController extends Controller
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
                            'actions' => ['index','view','create','update','delete','confirmar'],
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
     * Lists all LinhaFatura models.
     *
     * @return string
     */
    public function actionIndex()
    {
        $dataProvider = new ActiveDataProvider([
            'query' => LinhaFatura::find(),
            'pagination' => [
                'pageSize' => 20
            ],
            'sort' => [
                'defaultOrder' => [
                    'id' => SORT_DESC,
                ]
            ],
        ]);

        return $this->render('index', [
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single LinhaFatura model.
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
     * Creates a new LinhaFatura model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate($reserva_id,$tipo)
    {
        if (Fatura::faturaExistsForReservaId($reserva_id)) {
            // Caso exista uma fatura para esta reserva
            Yii::$app->session->setFlash('error', 'Não pode adicionar compras pois já existe uma fatura para esta reserva.');
            return $this->redirect(['reserva/index']);
        }

        $model = new LinhaFatura();
        $model->reserva_id = $reserva_id;
        $model->status = 'carrinho';
        

        if ($tipo === 'refeicao') {
            $refeicoes = Refeicao::find()->all();
            $servicos = null;
        } elseif ($tipo === 'servico') {
            $servicos = Servico::find()->all();
            $refeicoes = null;
        }   

        if ($this->request->isPost) {
            if ($model->load($this->request->post())) {
                $model->preco_unitario = $tipo === 'refeicao' ? $model->refeicao->preco : $model->servico->preco;
                $model->sub_total = $model->preco_unitario * $model->quantidade;
                $model->save();
                return $this->redirect(['view', 'id' => $model->id]);
            }
        } else {
            $model->loadDefaultValues();
        }

        return $this->render('create', [
            'model' => $model,
            'refeicoes' => $refeicoes,
            'servicos' => $servicos,
        ]);
    }

    /**
     * Updates an existing LinhaFatura model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return string|\yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($this->request->isPost && $model->load($this->request->post()) && $model->save()) {
            return $this->redirect(['view', 'id' => $model->id]);
        }

        if ($model->refeicao_id) {
            $refeicoes = Refeicao::find()->all();
            $servicos = null;
        } elseif ($model->servico_id) {
            $servicos = Servico::find()->all();
            $refeicoes = null;
        }  
        
        return $this->render('update', [
            'model' => $model,
            'refeicoes' => $refeicoes,
            'servicos' => $servicos,
        ]);
    }

    /**
     * Deletes an existing LinhaFatura model.
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
     * Finds the LinhaFatura model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return LinhaFatura the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = LinhaFatura::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }

    public function actionConfirmar($id)
    {
        $model = $this->findModel($id);
        $model->status = 'Confirmado';
        $model->save();

        return $this->redirect(['index']);
    }
}
