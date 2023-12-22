<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\LinhaFatura $model */

$reserva_id = Yii::$app->request->get('reserva_id');
$this->title = 'Create Linha Fatura';
$this->params['breadcrumbs'][] = ['label' => 'Linha Faturas', 'url' => ['index','reserva_id' => $reserva_id]];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="linha-fatura-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
        'refeicoes' => $refeicoes,
        'servicos' => $servicos,
    ]) ?>

</div>
