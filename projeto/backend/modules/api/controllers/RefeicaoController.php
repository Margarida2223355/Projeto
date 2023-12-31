<?php

namespace app\modules\api\controllers;

use common\models\Refeicao;
use yii\rest\ActiveController;

class RefeicaoController extends ActiveController
{
    public $modelClass = 'common\models\Refeicao';
    
    /* Método para obter refeições por data e horário */
    public function actionGetbydateschedule($date, $schedule) {
        return
            Refeicao::find()
                -> where(['data' => $date, 'categoria' => $schedule])
                -> all();
    }
}