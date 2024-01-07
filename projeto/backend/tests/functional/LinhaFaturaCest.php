<?php

namespace backend\tests\functional;

use backend\tests\FunctionalTester;
use common\fixtures\UserFixture;
use common\models\User;
use Yii;

class LinhaFaturaCest
{
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

        $I->amOnRoute('/linha-fatura/index');
    }
    
    public function checkIndex(FunctionalTester $I)
    {
        $I->see('Linha Faturas');
        $I->See('Confirmados');
        $I->See('Carrinhos');
        $I->See('Cancelados');
        $I->See('Todos');
    }

    public function checkUpdate(FunctionalTester $I)
    {
        $I->see('Linha Faturas');
        $I->click('Update');

        $I->fillField('LinhaFatura[quantidade]','4');
        $I->click('Save');

        $I->see('Quantidade');
        $I->see('4');

    }
    public function checkView(FunctionalTester $I)
    {
        $I->see('Linha Faturas');
        $I->click('View');
        $I->see('Sub Total');     

    }
    
}
