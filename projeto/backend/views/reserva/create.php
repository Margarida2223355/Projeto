<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\Reserva $model */

$this->title = 'Create Reserva';
$this->params['breadcrumbs'][] = ['label' => 'Reservas', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="Reserva-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
        'quartos' =>$quartos,
        'clientes'=>$clientes,
    ]) ?>

</div>
