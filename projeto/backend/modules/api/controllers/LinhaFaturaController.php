<?php

namespace app\modules\api\controllers;
use common\models\LinhaFatura;
use Yii;
use yii\rest\ActiveController;
use yii\web\Response;

class LinhaFaturaController extends ActiveController
{
    public $modelClass = 'common\models\LinhaFatura';

    public function actionLines($id) {
        $lines = LinhaFatura::find()
            -> where(['reserva_id' => $id])
            -> all();

        $linesAux = [];

        foreach($lines as $line) {
            $linesAux[] = [
                'id' => $line -> id,
                'quantidade' => $line -> quantidade,
                'servico' => is_null($line -> servico_id) ? 0 : $line -> servico,
                'refeicao' => is_null($line -> refeicao_id) ? 0 : $line -> refeicao,
                'sub_total' => $line -> sub_total,
                'preco_unitario' => $line -> preco_unitario,
                'reserva_id' => $line -> reserva_id,
                'status' => $line -> status
            ];
        }

        return $linesAux;
    }

    public function actionCreateline() {
        $requestData = Yii::$app -> request -> post();

        $id = $requestData['id'];
        $quantidade = $requestData['quantidade'];
        $servico_id = $requestData['servico_id'];
        $refeicao_id = $requestData['refeicao_id'];
        $sub_total = $requestData['sub_total'];
        $preco_unitario = $requestData['preco_unitario'];
        $reserva_id = $requestData['reserva_id'];
        $status = $requestData['status'];

        $line = new LinhaFatura();
        $line -> id = $id;
        $line -> quantidade = $quantidade;
        $line -> servico_id = $servico_id;
        $line -> refeicao_id = $refeicao_id;
        $line -> sub_total = $sub_total;
        $line -> preco_unitario = $preco_unitario;
        $line -> reserva_id = $reserva_id;
        $line -> status = $status;

        if($line -> save()) {
            return [
                'success' => true,
                'message' => 'Linha da fatura criada com sucesso',
                'line' => $line,
            ];
        }

        else {
            return [
                'success' => false,
                'message' => 'Erro ao adicionar linha',
            ];
        }
    }
}
