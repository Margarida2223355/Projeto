<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\LinhaFatura $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="linha-fatura-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'quantidade')->textInput(['type' => 'number', 'min' => 1]) ?>

    <?php if ($refeicoes !== null): ?>
        <?= $form->field($model, 'refeicao_id')->dropDownList(
            \yii\helpers\ArrayHelper::map($refeicoes, 'id', 'nome'),
            ['prompt' => 'Selecione uma refeição']
        ) ?>
    <?php endif; ?>

    <?php if ($servicos !== null): ?>
        <?= $form->field($model, 'servico_id')->dropDownList(
            \yii\helpers\ArrayHelper::map($servicos, 'id', 'nome'),
            ['prompt' => 'Selecione um serviço']
        ) ?>
    <?php endif; ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
