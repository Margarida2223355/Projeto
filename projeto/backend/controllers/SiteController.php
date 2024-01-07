<?php

namespace backend\controllers;

use backend\models\Custo;
use common\models\LoginForm;
use backend\models\SignupForm;
use common\models\Fatura;
use common\models\InfUser;
use common\models\Quarto;
use common\models\Refeicao;
use common\models\Reserva;
use common\models\Servico;
use Yii;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;
use yii\rbac\Role;
use yii\web\Controller;
use yii\web\Response;

/**
 * Site controller
 */
class SiteController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::class,
                'rules' => [
                    [
                        'actions' => ['login', 'error'],
                        'allow' => true,
                    ],
                    [
                        'actions' => ['index'],
                        'allow' => true,
                        'roles' => ['acederBackend'],
                    ],
                    [
                        'actions' => ['logout'],
                        'allow' => true,
                        'roles' => ['@'],
                    ],

                ],
            ],
            'verbs' => [
                'class' => VerbFilter::class,
                'actions' => [
                    'logout' => ['post'],
                ],
            ],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function actions()
    {
        return [
            'error' => [
                'class' => \yii\web\ErrorAction::class,
            ],
        ];
    }

    /**
     * Displays homepage.
     *
     * @return string
     */
    public function actionIndex()
    {
        $numeroUsers = InfUser::getTotalUsers();
        $totalReservas = Reserva::getTotalReservas();
        $numeroQuartos = Quarto::getTotalQuartos();
        $numeroRefeicoes = Refeicao::getTotalRefeicoes();
        $numeroServicos = Servico::getTotalServicos();
        $numeroFaturas = Fatura::getTotalFaturas();
        $reservasSemana = Reserva::getReservasSemanaAtual();
        $totalMesAtual = Custo::getTotalMes();

        return $this->render('index', ['numeroUsers' => $numeroUsers, 
        'totalReservas' => $totalReservas,'numeroQuartos'=>$numeroQuartos,
        'numeroRefeicoes'=>$numeroRefeicoes, 'numeroServicos'=>$numeroServicos,
        'numeroFaturas'=>$numeroFaturas, 'reservasSemana'=>$reservasSemana,
        'totalMesAtual'=>$totalMesAtual,
    ]);
    }

    /**
     * Login action.
     *
     * @return string|Response
     */
    public function actionLogin()
    {
        if (!Yii::$app->user->isGuest) {
            return $this->goHome();
        }

        $this->layout = 'blank';

        $model = new LoginForm();
        if ($model->load(Yii::$app->request->post()) && $model->login()) {
            if(Yii::$app->user->can('acederBackend')){
                return $this->goHome();
            }
            else{
                //Caso nao tenha permissao para aceder ao backend é forçado o logout
                Yii::$app->user->logout();
            }
        }

        $model->password = '';

        return $this->render('login', [
            'model' => $model,
        ]);
    }

    /**
     * Logout action.
     *
     * @return Response
     */
    public function actionLogout()
    {
        Yii::$app->user->logout();

        return $this->goHome();
    }
    public function actionSignup()
    {
        $model = new SignupForm();
        if ($model->load(Yii::$app->request->post()) && $model->signup()) {
            Yii::$app->session->setFlash('success', 'Thank you for registration. Please check your inbox for verification email.');
            return $this->goHome();
        }

        return $this->render('signup', [
            'model' => $model,
        ]);
    }
}
