<?php

namespace frontend\tests\functional;

use frontend\tests\FunctionalTester;
use common\models\User;
use common\models\Reserva;
use Yii;

class ReservaCest
{

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
        // Criar um utilizador de teste
        $user = $I->haveRecord(User::class, [
            'username' => 'testuser',
            'email' => 'testuser@example.com',
            'password_hash' => Yii::$app->security->generatePasswordHash('testpassword'),
        ]);

        // Autenticar como o utilizador criado
        $I->amLoggedInAs($user);

        $I->amOnPage('/index.php?r=reserva%2Fquartos-disponiveis&dataInicial=2024-01-13&dataFinal=2024-01-13');

        // Verificar se aparecem os quartos
        $I->see('Lista de Quartos');
        $I->click('Create Reserva');

        $I->see('Create Reserva');
        $I->see('Preco Total');
        $I->click('Save');

        $I->seeRecord('common\models\Reserva', [
            'data_inicial' => '2024-01-13',
            'data_final' => '2024-01-13',
        ]);
    }
    public function checkUpdate(FunctionalTester $I)
    {
        // Criar um utilizador de teste
        $user = $I->haveRecord(User::class, [
            'username' => 'testuser',
            'email' => 'testuser@example.com',
            'password_hash' => Yii::$app->security->generatePasswordHash('testpassword'),
        ]);

        // Autenticar como o utilizador criado
        $I->amLoggedInAs($user);

        $I->amOnPage('/index.php?r=reserva%2Fquartos-disponiveis&dataInicial=2024-01-13&dataFinal=2024-01-13');

        // Verificar se aparecem os quartos
        $I->see('Lista de Quartos');
        $I->click('Create Reserva');

        $I->see('Create Reserva');
        $I->see('Preco Total');
        $I->click('Save');

        $I->see('Reserva');
        $I->see('ID');
        $I->see('Status');
        $I->see('Total');
        
        $I->click('Update');

        $I->see('Update Reserva');

        $I->fillField('Reserva[data_final', '2024-01-14');
        $I->fillField('Reserva[data_final', '2024-01-14');

        $I->click('Save');

        $I->seeRecord('common\models\Reserva', [
            'data_inicial' => '2024-01-14',
            'data_final' => '2024-01-14',
        ]);
    }
}
