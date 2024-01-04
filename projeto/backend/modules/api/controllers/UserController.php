<?php

namespace app\modules\api\controllers;
use common\models\InfUser;
use common\models\User;
use yii\filters\auth\HttpBasicAuth;
use yii\rest\ActiveController;
use yii\web\ForbiddenHttpException;

class UserController extends ActiveController
{
    public $modelClass = 'common\models\InfUser';
    //private $user = null;

    public function behaviors() {
        $behaviors = parent::behaviors();

        $behaviors['authenticator'] = [
            'class' => HttpBasicAuth::className(),
            'auth' => [$this, 'auth']
        ];

        return $behaviors;
    }

    public function auth($username, $password) {
        $user = User::findByUsername($username);

        if ($user && $user -> validatePassword($password)) {
            $this -> user = $user;
            return $user;
        }

        throw new ForbiddenHttpException('403: No authentication!');
    }

    public function actionLogin() {
        return InfUser::findOne([
            'id' => $this -> user -> id
        ]);
    }
}