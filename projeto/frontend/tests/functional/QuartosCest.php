<?php

namespace frontend\tests\functional;

use frontend\tests\FunctionalTester;

class QuartosCest
{

    public function checkOpen(FunctionalTester $I)
    {
        $I->amOnRoute('site/quartos');
        $I->see('Lista de Quartos');
        $I->see('Preço:');
        $I->see('Camas de casal');
        $I->see('Camas de solteiro');
        $I->see('Ar condicionado');
        $I->see('Aquecedor');
    }
}
