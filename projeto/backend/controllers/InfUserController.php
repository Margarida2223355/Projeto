<?php

namespace backend\controllers;

use common\models\infUser;
use backend\models\SignupForm;
use common\models\User;
use Yii;
use yii\db\Query;
use yii\data\ActiveDataProvider;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * InfUserController implements the CRUD actions for infUser model.
 */
class InfUserController extends Controller
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
                            'actions' => ['index','view','create','update','delete','set-status'],
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
     * Lists all infUser models.
     *
     * @return string
     */
    public function actionIndex()
    {
              $dataProvider = new ActiveDataProvider([
            'query' => infUser::find(),
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
     * Displays a single infUser model.
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
     * Creates a new infUser model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return string|\yii\web\Response
     */
    public function actionCreate()
    {
        $model = new SignupForm();
        $model->role = Yii::$app->authManager->getRole('cliente')->name;
        if ($model->load(Yii::$app->request->post()) && $model->signup()) {
            Yii::$app->session->setFlash('success', 'Thank you for registration. Please check your inbox for verification email.');
            return $this->goHome();
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing infUser model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param int $id ID
     * @return string|\yii\web\Response
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($this->request->isPost && $model->load($this->request->post()) && $model->save()) {
            $auth = Yii::$app->authManager;
            
            if (Yii::$app->user->can('gestor')) {
                 // Pega a role diretamente dos dados POST (quando tentei buscar a role do $model->role ele mostrava a role que estava salva na db ao inves da nova)
                 $roleFromPost = $this->request->post('InfUser')['role'];
            
                // Remove todas as roles existentes do usuário
                $auth->revokeAll($model->id);

                // Atribui a nova role ao usuário com base no valor do formulário
                $newRole = $auth->getRole($roleFromPost);
                $auth->assign($newRole, $model->id);
            }
            $model->user->username = $this->request->post('InfUser')['username'];
            $model->user->email = $this->request->post('InfUser')['email'];
            $model->user->save();

            return $this->redirect(['view', 'id' => $model->id]);
        }

        return $this->render('update', [
            'model' => $model,
        ]);
    }

    /**
     * Deletes an existing infUser model.
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

    public function actionSetStatus($id){
        $user = User::findOne($id);
        $user->status = ($user->status == 10) ? 9 : 10;
        $user->save();        
        $dataProvider = new ActiveDataProvider([
            'query' => infUser::find(),]);
        return $this->render('index', [
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Finds the infUser model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param int $id ID
     * @return infUser the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = infUser::findOne(['id' => $id])) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
