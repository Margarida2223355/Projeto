<?php

namespace backend\tests\functional;

use backend\tests\FunctionalTester;
use common\fixtures\UserFixture;
use common\models\Quarto;
use Yii;

class QuartoCest
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

        $I->amOnRoute('/quarto/index');
    }
    
    public function checkIndex(FunctionalTester $I)
    {
        $I->see('Quartos');
        $I->See('Create Quarto');
    }
    public function checkCreate(FunctionalTester $I)
    {
        $I->click('Create Quarto');
        $I->fillField('Quarto[descricao]', 'Quarto Teste');
        $I->fillField('Quarto[camas_solteiro]', 1);
        $I->fillField('Quarto[camas_casal]', 1);
        $I->fillField('Quarto[preco]', 10);
        $I->selectOption('Quarto[arcondicionado]', 'Disponivel');
        $I->selectOption('Quarto[aquecedor]', 'Disponivel');

        $I->click('Save');
        $I->seeRecord(Quarto::className(), ['descricao'=>'Quarto Teste']);
    }
    public function checkUpdate(FunctionalTester $I)
    {
        $I->click('Update');
        $I->fillField('Quarto[descricao]', 'Quarto Teste update');
        $I->fillField('Quarto[camas_solteiro]', 1);
        $I->fillField('Quarto[camas_casal]', 1);
        $I->fillField('Quarto[preco]', 10);
        $I->selectOption('Quarto[arcondicionado]', 'Disponivel');
        $I->selectOption('Quarto[aquecedor]', 'Disponivel');

        $I->click('Save');
        $I->seeRecord(Quarto::className(), ['descricao'=>'Quarto Teste update']);
    }
    
}
