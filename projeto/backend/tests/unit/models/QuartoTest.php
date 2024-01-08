<?php

namespace backend\tests\unit\models;

use common\models\Quarto;
use common\models\Reserva;
use Yii;

class QuartoTest extends \Codeception\Test\Unit
{
    protected $tester;

    public function testQuartosDisponiveis()
    {
        // Obter todos os quartos existentes
        $todosQuartos = Quarto::find()->all();

        // Cria reservas para todos os quartos na mesma data
        $dataInicial = '2024-01-13';
        $dataFinal = '2024-01-15';

        foreach ($todosQuartos as $quarto) {
            $reserva = new Reserva([
                'quarto_id' => $quarto->id,
                'cliente_id' => 1,
                'data_inicial' => $dataInicial,
                'data_final' => $dataFinal,
                'status' => 'Inativo',
            ]);
            $reserva->save(false);
        }
        // Deve retornar uma lista de quartos disponíveis
        $quartosDisponiveis = count(Quarto::quartosDisponiveis('2024-01-13', '2024-01-15'));

        $this->assertEquals( 0, $quartosDisponiveis);
        // Limpar as reservas criadas
        Reserva::deleteAll(['data_inicial' => $dataInicial, 'data_final' => $dataFinal]);
    }

    public function testGetTotalQuartos()
    {
        // Obter o número atual de quartos na base de dados
        $totalQuartosNoBanco = Quarto::getTotalQuartos();

        // Criar alguns quartos de teste
        $quarto1 = new Quarto([
            'descricao' => 'Quarto Teste 1',
            'camas_solteiro' => 2,
            'camas_casal' => 1,
            'arcondicionado' => 1,
            'aquecedor' => 1,
            'preco' => 150.0,
        ]);
        $quarto1->save(false);

        $quarto2 = new Quarto([
            'descricao' => 'Quarto Teste 2',
            'camas_solteiro' => 3,
            'camas_casal' => 0,
            'arcondicionado' => 0,
            'aquecedor' => 1,
            'preco' => 120.0,
        ]);
        $quarto2->save(false);

        // Chamar o método para obter o total de quartos
        $totalQuartos = Quarto::getTotalQuartos();

        // Apagar os quartos feitos para o teste
        $quarto1->delete();
        $quarto2->delete();
        // Deve retornar o número total de quartos criados
        $this->assertEquals($totalQuartosNoBanco +2, $totalQuartos);
    }

}
