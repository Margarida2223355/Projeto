<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var app\models\Custo $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="custo-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'descricao_custo_id')->textInput() ?>

    <?= $form->field($model, 'valor')->textInput() ?>

    <?= $form->field($model, 'data')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
