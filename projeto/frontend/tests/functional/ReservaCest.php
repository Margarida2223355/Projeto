<?php

namespace frontend\tests\functional;

use frontend\tests\FunctionalTester;
use common\models\User;
use Yii;

class ReservaCest
{
    protected $formId = '#reserva';

    public function checkOpenWithoutLogging(FunctionalTester $I)
    {
        //Verificar a pagina de reserva
        $I->amOnRoute('reserva/index');
        $I->cantSee('Reservas');
        $I->cantSee('Data Inicial');
        $I->cantSee('Data Final');
        $I->cantSee('Create Reserva');
        $I->see('Login');
        $I->see('Please fill out the following fields to login:');
        
    }

    public function checkOpen(FunctionalTester $I)
    {
        // Criar um utilizador de teste
        $user = $I->haveRecord(User::class, [
            'username' => 'testuser',
            'email' => 'testuser@example.com',
            'password_hash' => Yii::$app->security->generatePasswordHash('testpassword'),
        ]);

        // Autenticar como o utilizador criado
        $I->amLoggedInAs($user);

        //Verificar a pagina de reserva
        $I->amOnRoute('reserva/index');
        $I->see('Reservas');
        $I->see('Data Inicial');
        $I->see('Data Final');
        $I->seeLink('Create Reserva');
        
    }
    
    public function checkCreate(FunctionalTester $I)
    {
        $I->amOnRoute('reserva/index');

        // Preencher as datas para procura de quartos disponiveis para reserva
        $I->fillField('Data Inicial', '2023-01-13');
        $I->fillField('Data Final', '2023-01-13');
        $I->click('Create Reserva');

        $I->seeCurrentUrlEquals('/reserva/create');

        // Verificar se aparecem os quartos
        $I->see('Lista de Quartos');
        $I->click('Create Reserva');

        $I->see('Create Reserva');
        $I->see('Preco Total');
        $I->click('Save');

        $I->seeRecord('common\models\Reserva', [
            'data_inicial' => '2023-01-13',
            'data_final' => '2023-01-13',
        ]);
    }
}
