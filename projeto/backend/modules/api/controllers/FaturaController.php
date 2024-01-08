<?php

namespace app\modules\api\controllers;
use common\models\Fatura;
use Yii;
use yii\rest\ActiveController;

class FaturaController extends ActiveController
{
    public $modelClass = 'common\models\Fatura';

    public function actionInvoices() {
        $requestData = Yii::$app -> request -> post();

        $id = $requestData['id'];
        $pousada_id = $requestData['pousada_id'];
        $payment_date = $requestData['data_pagamento'];
        $reserva_id = $requestData['reserva_id'];
        $total = $requestData['preco_total'];

        $invoice = new Fatura();
        $invoice -> id = $id;
        $invoice -> pousada_id = $pousada_id;
        $invoice -> data_pagamento = $payment_date;
        $invoice -> reserva_id = $reserva_id;
        $invoice -> preco_total = $total;

        if($invoice -> save()) {
            return [
                'success' => true,
                'message' => 'Fatura criada com sucesso',
            ];
        }

        else {
            return [
                'success' => false,
                'message' => 'Erro ao criar fatura',
            ];
        }

    }
}
