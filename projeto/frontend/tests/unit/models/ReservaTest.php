<?php

namespace frontend\tests\unit\models;

use common\fixtures\UserFixture;
use common\models\Quarto;
use common\models\Reserva;
use common\models\User;
use frontend\models\VerifyEmailForm;
use Yii;

class ReservaTest extends \Codeception\Test\Unit
{
    /**
     * @var \frontend\tests\UnitTester
     */
    protected $tester;


    protected function _before()
    {
        parent::_before();
        // Limpar a tabelas antes de cada teste
        Yii::$app->db->createCommand()->truncateTable('reserva')->execute();
        Yii::$app->db->createCommand()->truncateTable('linha_fatura')->execute();
    }
    protected function _after()
    {
        parent::_after();
        // Limpar a tabelas depois de cada teste
        Yii::$app->db->createCommand()->truncateTable('reserva')->execute();
    }

    public function testCalcularTotal()
    {
        $user = new User([
            'username' => 'testuser',
            'email' => 'testuser@example.com',
            'password_hash' => Yii::$app->security->generatePasswordHash('testpassword'),
        ]);
        $user->save(false);

        $quarto = new Quarto([
            'descricao' => 'quarto 1',
            'camas_solteiro' => 1,
            'camas_casal' => 1,
            'arcondicionado' => 1,
            'aquecedor' => 1,
            'preco' => 100.0,
        ]);
        $quarto->save(false);

        $reserva = new Reserva([
            'quarto_id' => $quarto->id,
            'cliente_id' => $user->id,
            'data_inicial' => '2024-01-13',
            'data_final' => '2024-01-15',
            'status' => 'Ativa',
        ]);

        $reserva->save(false);

        $totalCalculado = $reserva->calcularTotal();

        // Supondo 2 dias de reserva a 100 por dia
        $totalEsperado = 2 * $quarto->preco;

        // Verificar se o total calculado coincide com o total esperado
        $this->assertEquals($totalEsperado, $totalCalculado);
    }

    public function testGetReservasSemanaAtual()
    {
        // Criar uma reserva de teste para a semana atual
        $reservaSemanaAtual = new Reserva([
            'quarto_id' => 1, 
            'cliente_id' => 1, 
            'data_inicial' => date('Y-m-d'),
            'data_final' => date('Y-m-d', strtotime('+2 days')),
            'status' => 'Ativo',
        ]);
        $reservaSemanaAtual->save(false);

        // Criar uma reserva de teste fora da semana atual
        $reservaOutraSemana = new Reserva([
            'quarto_id' => 1,
            'cliente_id' => 1,
            'data_inicial' => date('Y-m-d', strtotime('+8 days')), // Próxima semana
            'data_final' => date('Y-m-d', strtotime('+10 days')), // Reserva de 3 dias
            'status' => 'Ativo',
        ]);
        $reservaOutraSemana->save(false);

        // Chamar o método para obter o número de reservas da semana atual
        $reservasSemanaAtual = Reserva::getReservasSemanaAtual();

        // Deve haver uma reserva para a semana atual
        $this->assertEquals(1, $reservasSemanaAtual);
    }
    
    public function testCalculaDiarias()
    {
        $quarto = new Quarto([
            'descricao' => 'quarto 1',
            'camas_solteiro' => 1,
            'camas_casal' => 1,
            'arcondicionado' => 1,
            'aquecedor' => 1,
            'preco' => 100.0,
        ]);
        $quarto->save(false);

        $reserva = new Reserva([
            'quarto_id' => $quarto->id,
            'cliente_id' => 1,
            'data_inicial' => '2024-01-13',
            'data_final' => '2024-01-15',
            'status' => 'Ativo',
        ]);
        $reserva->save(false);

        // Chamar o método para calcular o total de diárias
        $precoDiarias = $reserva->calculaDiarias();

        // Deve ser igual a 2 dias de reserva a 100 por dia
        $this->assertEquals(200.0, $precoDiarias);
    }
}
