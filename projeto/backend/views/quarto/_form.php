<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var common\models\Quarto $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="quarto-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'descricao')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'camas_solteiro')->textInput() ?>

    <?= $form->field($model, 'camas_casal')->textInput() ?>

    <?= $form->field($model, 'arcondicionado')->dropDownList(['1' => 'Disponivel', '0' => 'Indisponivel']) ?>

    <?= $form->field($model, 'aquecedor')->dropDownList(['1' => 'Disponivel', '0' => 'Indisponivel']) ?>

    <?= $form->field($model, 'preco')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
