<?php

namespace app\modules\api\controllers;
use common\models\Quarto;
use common\models\Reserva;
use yii\rest\ActiveController;

class ReservaController extends ActiveController
{
    public $modelClass = 'common\models\Reserva';

    public function actionGetreservationbydates($init_date, $end_date) {
        $reservas = Reserva::find()
            -> where(['between', 'data_inicial', $init_date, $end_date])
            -> all();


        $reservasAux = [];

        foreach($reservas as $reserva) {
            $reservasAux[] = [
                'id' => $reserva -> id,
                'quarto' => $reserva -> quarto,
                'cliente' => $reserva -> cliente,
                'data_inicial' => $reserva -> data_inicial,
                'data_final' => $reserva -> data_final,
                'preco_total' => $reserva -> preco_total,
                'status' => $reserva -> status,
            ];
        }

        return $reservasAux;
    }
}