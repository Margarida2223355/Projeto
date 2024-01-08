<?php

namespace frontend\tests\acceptance;

use Exception;
use frontend\tests\AcceptanceTester;
use Yii;
use yii\helpers\Url;

class ReservaCest
{
    public function createReserva(AcceptanceTester $I)
    {

        $I->amOnPage('/');
        $I->see('Pousada');

        $I->click('Login');
        $I->wait(2); // Espera para carregar a pagina

        // Preenche os dados de login
        $I->fillField('LoginForm[username]','cliente');
        $I->fillField('LoginForm[password]','12345678');
        $I->click('Entrar');
        $I->wait(2);

        // Inserir a data que deseja reservar
        $I->click('Reserva');
        $I->wait(2);
        $I->fillField('Reserva[data_inicial]','2024-02-23');
        $I->fillField('Reserva[data_final]','2024-02-24');
        $I->click('Create Reserva');
        $I->wait(4);

        // Escolher o quarto e confirmar a reserva
        $I->wait(5);
        $I->seeElement('.btn.btn-success');
        $I->executeJS('document.querySelector(".btn.btn-success").click();');
        $I->wait(5);
        $I->executeJS('document.querySelector(".btn.btn-success").click();');
        $I->wait(5);

    }
}
