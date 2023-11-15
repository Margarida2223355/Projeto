<?php

namespace frontend\models;

use Yii;
use yii\base\Model;
use common\models\User;
use common\models\infUser;

/**
 * Signup form
 */
class SignupForm extends Model
{
    public $username;
    public $email;
    public $password;
    public $nome_completo; 
    public $morada;        
    public $pais;
    public $telefone;
    public $nif;
    public $salario;


    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            ['username', 'trim'],
            ['username', 'required'],
            ['username', 'unique', 'targetClass' => '\common\models\User', 'message' => 'This username has already been taken.'],
            ['username', 'string', 'min' => 2, 'max' => 255],

            ['email', 'trim'],
            ['email', 'required'],
            ['email', 'email'],
            ['email', 'string', 'max' => 255],
            ['email', 'unique', 'targetClass' => '\common\models\User', 'message' => 'This email address has already been taken.'],

            ['password', 'required'],
            ['password', 'string', 'min' => Yii::$app->params['user.passwordMinLength']],
            
            [['nome_completo', 'morada', 'pais', 'telefone', 'nif'], 'required'],
            [['nome_completo'], 'string', 'max' => 150],
            [['morada'], 'string', 'max' => 100],
            [['pais'], 'string', 'max' => 50],
            [['telefone'], 'string', 'max' => 20],
            [['nif'], 'string', 'max' => 15],
        ];
    }

    /**
     * Signs user up.
     *
     * @return bool whether the creating new account was successful and email was sent
     */
    public function signup()
    {
        if (!$this->validate()) {
            return null;
        }
        
        $user = new User();
        $user->username = $this->username;
        $user->email = $this->email;
        $user->setPassword($this->password);
        $user->generateAuthKey();
        $user->generateEmailVerificationToken();
        
        $infUser = new infUser();
        $infUser->nome_completo = $this->nome_completo;
        $infUser->morada = $this->morada;
        $infUser->pais = $this->pais;
        $infUser->telefone = $this->telefone;
        $infUser->nif = $this->nif;
        $infUser->salario = $this->salario;

        if ($user->save() && $this->sendEmail($user) && ($infUser->id = $user->id) !== null && $infUser->save()) {
            $auth = Yii::$app->authManager;
            $clienteRole = $auth->getRole('cliente'); 
            $auth->assign($clienteRole, $user->getId());
            
            return true;
        } else {
            return null;
        }
    }

    /**
     * Sends confirmation email to user
     * @param User $user user model to with email should be send
     * @return bool whether the email was sent
     */
    protected function sendEmail($user)
    {
        return Yii::$app
            ->mailer
            ->compose(
                ['html' => 'emailVerify-html', 'text' => 'emailVerify-text'],
                ['user' => $user]
            )
            ->setFrom([Yii::$app->params['supportEmail'] => Yii::$app->name . ' robot'])
            ->setTo($this->email)
            ->setSubject('Account registration at ' . Yii::$app->name)
            ->send();
    }
}
