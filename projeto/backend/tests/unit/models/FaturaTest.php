<?php

namespace backend\tests\unit\models;

use common\models\Fatura;
use common\models\Reserva;
use common\models\Pousada;
use DateTime;

class FaturaTest extends \Codeception\Test\Unit
{
    protected $tester;

    public function testFaturaExistsForReservaId()
    {
        // Criar uma reserva de teste
        $reserva = new Reserva([
            'quarto_id' => 1,
            'cliente_id' => 1,
            'data_inicial' => '2024-01-13',
            'data_final' => '2024-01-15',
            'status' => 'Inativo',
        ]);
        $reserva->save(false);

        // Criar uma fatura associada à reserva
        $fatura = new Fatura([
            'data_pagamento' => '2024-01-16',
            'preco_total' => 200.0,
            'reserva_id' => $reserva->id,
            'pousada_id' => 1,
        ]);
        $fatura->save(false);

        $exists = Fatura::faturaExistsForReservaId($reserva->id);

        // Deve retornar true, pois criamos uma fatura para essa reserva
        $this->assertTrue($exists);

        // Limpar dados de teste
        $fatura->delete();
        $reserva->delete();
    }

    public function testGetTotalFaturas()
    {
        // Obter o número atual de faturas na base de dados
        $totalFaturasNoBanco = Fatura::getTotalFaturas();

        // Criar uma reserva de teste
        $reserva = new Reserva([
            'quarto_id' => 1,
            'cliente_id' => 1,
            'data_inicial' => '2024-01-13',
            'data_final' => '2024-01-15',
            'status' => 'Inativo',
        ]);
        $reserva->save(false);

        // Criar uma fatura de teste
        $fatura = new Fatura([
            'data_pagamento' => '2024-01-16',
            'preco_total' => 200.0,
            'reserva_id' => $reserva->id,
            'pousada_id' => 1,
        ]);
        $fatura->save(false);

        $totalFaturas = Fatura::getTotalFaturas();

        // Deve retornar o número total de faturas criadas
        $this->assertEquals($totalFaturasNoBanco + 1, $totalFaturas);

        // Limpar dados de teste
        $fatura->delete();
        $reserva->delete();
    }
}
