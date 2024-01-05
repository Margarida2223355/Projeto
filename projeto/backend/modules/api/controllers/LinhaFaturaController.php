<?php

namespace app\modules\api\controllers;
use common\models\LinhaFatura;
use yii\rest\ActiveController;

class LinhaFaturaController extends ActiveController
{
    public $modelClass = 'common\models\LinhaFatura';

    public function actions() {
        $actions = parent::actions();
        unset($actions['index']);
        return $actions;
    }

    public function actionIndex() {
        $lines = LinhaFatura::find()->all();

        $linesAux = [];

        foreach($lines as $line) {
            $linesAux[] = [
                'id' => $line -> id,
                'quantidade' => $line -> quantidade,
                'servico' => $line -> servico_id = is_null($line -> servico_id) ? 0 : $line -> servico,
                'refeicao' => $line -> refeicao_id = is_null($line -> refeicao_id) ? 0 : $line -> refeicao,
                'sub_total' => $line -> sub_total,
                'preco_unitario' => $line -> preco_unitario,
                'reserva_id' => $line -> reserva_id,
                'status' => $line -> status
            ];
        }

        return $linesAux;
    }
}
