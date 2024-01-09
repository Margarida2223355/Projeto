<?php

namespace frontend\tests\unit\models;

use common\models\LinhaFatura;
use common\models\Reserva;
use Yii;

class LinhaFaturaTest extends \Codeception\Test\Unit
{
    /**
     * @var \frontend\tests\UnitTester
     */
    protected $tester;


    protected function _before()
    {
        parent::_before();
        // Limpar a tabela de reservas antes de cada teste
        Yii::$app->db->createCommand()->truncateTable('reserva')->execute();
        Yii::$app->db->createCommand()->truncateTable('linha_fatura')->execute();
    }

    public function testVerificarPedidosConfirmados()
    {
        $reserva = new Reserva([
            'quarto_id' => 1,
            'cliente_id' => 1, 
            'data_inicial' => '2024-01-13',
            'data_final' => '2024-01-15',
            'status' => 'Ativa',
        ]);
        $reserva->save(false);

        $linhaFatura = new LinhaFatura([
            'quantidade' => 1,
            'servico_id' => 1, 
            'sub_total' => 100.0,
            'preco_unitario' => 100.0,
            'reserva_id' => $reserva->id,
            'status' => 'Confirmado',
        ]);
        $linhaFatura->save(false);

        $result = LinhaFatura::verificarPedidosConfirmados($reserva->id);

        $this->assertTrue($result);
    }
    public function testVerificarPedidoConfirmado()
    {
         $reserva = new Reserva([
            'quarto_id' => 1, 
            'cliente_id' => 1, 
            'data_inicial' => '2024-01-13',
            'data_final' => '2024-01-15',
            'status' => 'Ativa',
        ]);
        $reserva->save(false);

        $linhaFatura = new LinhaFatura([
            'quantidade' => 1,
            'refeicao_id' => 1,
            'sub_total' => 100.0,
            'preco_unitario' => 100.0,
            'reserva_id' => $reserva->id,
            'status' => 'Confirmado',
        ]);
        $linhaFatura->save(false);

        $result = LinhaFatura::verificarPedidoConfirmado($linhaFatura->id);

        // Deve retornar true, pois a linha de fatura tem status Confirmado
        $this->assertTrue($result);
    }
}
