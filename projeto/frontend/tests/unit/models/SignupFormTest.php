<?php

namespace frontend\tests\unit\models;

use common\fixtures\UserFixture;
use common\fixtures\InfUserFixture;
use common\models\InfUser;
use frontend\models\SignupForm;
use Yii;

class SignupFormTest extends \Codeception\Test\Unit
{
    /**
     * @var \frontend\tests\UnitTester
     */
    protected $tester;


    public function _before()
    {
        $this->tester->haveFixtures([
            'user' => [
                'class' => UserFixture::class,
                'dataFile' => codecept_data_dir() . 'user.php'
            ],
            'infUser' => [
                'class' => InfUserFixture::class,
                'dataFile' => codecept_data_dir() . 'infUser.php'
            ]
        ]);
    }

    public function testCorrectSignup()
    {
        $model = new SignupForm([
            'username' => 'some_username',
            'email' => 'some_email@example.com',
            'password' => 'some_password',
            'nome_completo'  => 'name tester',
            'morada'  => 'rua tester',
            'pais'  => 'pais tester',
            'telefone'  => '66666666',
            'nif'  => '999999666',
        ]);

        $user = $model->signup();
        verify($user)->notEmpty();

        /** @var \common\models\User $user */
        $user = $this->tester->grabRecord('common\models\User', [
            'username' => 'some_username',
            'email' => 'some_email@example.com',
            'status' => \common\models\User::STATUS_INACTIVE
        ]);
        $infUser = $this->tester->grabRecord('common\models\InfUser', [
            'nome_completo' => 'name tester',
            'morada' => 'rua tester',
            'pais' => 'pais tester',
            'telefone' => '66666666',
            'nif' => '999999666',
        ]);

        $this->tester->seeEmailIsSent();

        //apagar role da db
        $authManager = Yii::$app->authManager;
        $role = $authManager->getRole('cliente');
        $authManager->revoke($role, $user->id);

        $mail = $this->tester->grabLastSentEmail();

        verify($mail)->instanceOf('yii\mail\MessageInterface');
        verify($mail->getTo())->arrayHasKey('some_email@example.com');
        verify($mail->getFrom())->arrayHasKey(\Yii::$app->params['supportEmail']);
        verify($mail->getSubject())->equals('Account registration at ' . \Yii::$app->name);
        verify($mail->toString())->stringContainsString($user->verification_token);
    }

    public function testNotCorrectSignup()
    {
        $model = new SignupForm([
            'username' => 'troy.becker',
            'email' => 'nicolas.dianna@hotmail.com',
            'password' => 'some_password',
        ]);

        verify($model->signup())->empty();
        verify($model->getErrors('username'))->notEmpty();
        verify($model->getErrors('email'))->notEmpty();

        verify($model->getFirstError('username'))
            ->equals('This username has already been taken.');
        verify($model->getFirstError('email'))
            ->equals('This email address has already been taken.');
    }
}
