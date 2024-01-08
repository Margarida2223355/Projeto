<?php

namespace backend\tests\functional;

use backend\tests\FunctionalTester;
use common\fixtures\UserFixture;

/**
 * Class LoginCest
 */
class LoginCest
{
    /**
     * Load fixtures before db transaction begin
     * Called in _before()
     * @see \Codeception\Module\Yii2::_before()
     * @see \Codeception\Module\Yii2::loadFixtures()
     * @return array
     */
    public function _fixtures()
    {
        return [
            'user' => [
                'class' => UserFixture::class,
                'dataFile' => codecept_data_dir() . 'login_data.php'
            ]
        ];
    }
    
    /**
     * @param FunctionalTester $I
     */
    public function loginUser(FunctionalTester $I)
    {
        $I->amOnRoute('/site/login');
        $I->fillField('Username', 'erau');
        $I->fillField('Password', 'password_0');
        $I->click('login-button');

        $I->see('Home');
        $I->dontSeeLink('Login');
        $I->dontSeeLink('Signup');
    }
    public function testUserDoesNotHavePermission(FunctionalTester $I){
    
        $I->amOnRoute('/site/login');
        // Não preenche o campo de login
        $I->fillField('Username', 'cliente');
        $I->fillField('Password', '12345678');
        $I->click('login-button');

        $I->seeElement('#login-form'); // Verifica se o formulário ainda está visível

        // Certifique-se de que o login não foi bem-sucedido
        $I->seeCurrentUrlEquals('/index-test.php/site/login');
        $I->dontSee('Home');
        $I->see('Login');
    }
    protected function formParams($login, $password)
    {
        return [
            'LoginForm[username]' => $login,
            'LoginForm[password]' => $password,
        ];
    }
    public function checkEmpty(FunctionalTester $I)
    {
        $I->amOnRoute('site/login');
        $I->submitForm('#login-form', $this->formParams('', ''));
        $I->seeValidationError('Username cannot be blank.');
        $I->seeValidationError('Password cannot be blank.');
    }

    public function checkWrongPassword(FunctionalTester $I)
    {
        $I->amOnRoute('site/login');
        $I->submitForm('#login-form', $this->formParams('admin', 'wrong'));
        $I->seeValidationError('Incorrect username or password.');
    }

    public function checkInactiveAccount(FunctionalTester $I)
    {
        $I->amOnRoute('site/login');
        $I->submitForm('#login-form', $this->formParams('test.test', 'Test1234'));
        $I->seeValidationError('Incorrect username or password');
    }
}
