<?php

namespace backend\tests\functional;

use backend\tests\FunctionalTester;
use common\fixtures\UserFixture;
use common\models\User;
use Yii;

class UserCest
{
    protected $formId = '#form-signup';
    public function _fixtures()
    {
        return [
            'user' => [
                'class' => UserFixture::class,
                'dataFile' => codecept_data_dir() . 'login_data.php'
            ]
        ];
    }
    public function _before(FunctionalTester $I)
    {
        $I->amOnRoute('/site/login');
        $I->fillField('Username', 'erau');
        $I->fillField('Password', 'password_0');
        $I->click('login-button');

        $I->amOnRoute('/inf-user/index');
    }
    
    public function checkIndex(FunctionalTester $I)
    {
        $I->see('Inf Users');
        $I->See('Create Inf User');
    }
    public function checkCreate(FunctionalTester $I)
    {
        $I->see('Inf Users');
        $I->click('Create Inf User');

        $I->submitForm($this->formId, [
            'SignupForm[username]' => 'tester',
            'SignupForm[email]' => 'tester.email@example.com',
            'SignupForm[password]' => 'tester_password',
            'SignupForm[nome_completo]'  => 'tester tester',
            'SignupForm[morada]'  => 'rua tester',
            'SignupForm[pais]'  => 'pais tester',
            'SignupForm[telefone]'  => '66666666',
            'SignupForm[nif]'  => '999999666',
        ]);

        $I->seeRecord('common\models\User', [
            'username' => 'tester',
            'email' => 'tester.email@example.com',
            'status' => \common\models\User::STATUS_INACTIVE
        ]);
        $I->seeRecord('common\models\infUser', [
        'nome_completo' => 'tester tester',
            'morada' => 'rua tester',
            'pais' => 'pais tester',
            'telefone' => '66666666',
            'nif' => '999999666',
        ]);
    }
    
}
