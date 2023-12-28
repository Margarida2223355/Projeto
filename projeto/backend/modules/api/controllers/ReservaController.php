<?php

namespace app\modules\api\controllers;
use common\models\Quarto;
use common\models\Reserva;
use yii\rest\ActiveController;

class ReservaController extends ActiveController
{
    public $modelClass = 'common\models\Reserva';

    public function actions()
    {
        $actions = parent::actions();

        // Desabilitar a ação padrão "index"
        unset($actions['index']);

        return $actions;
    }

    public function actionIndex() {
        $reservas = Reserva::find() -> all();

        $reservasAux = [];

        foreach($reservas as $reserva) {
            $reservasAux[] = [
                'id' => $reserva -> id,
                'quarto' => $reserva -> quarto -> descricao,
                'cliente' => $reserva -> cliente -> nome_completo,
                'data_inicial' => $reserva -> data_inicial,
                'data_final' => $reserva -> data_final,
                'preco_total' => $reserva -> preco_total,
                'status' => $reserva -> status,
            ];
        }

        return $reservasAux;
    }
}