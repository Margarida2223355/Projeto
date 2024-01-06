<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/** @var yii\web\View $this */
/** @var app\models\DescricaoCusto $model */
/** @var yii\widgets\ActiveForm $form */
?>

<div class="descricao-custo-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'descricao')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'fornecedor_id')->dropDownList(
    \yii\helpers\ArrayHelper::map(\backend\models\Fornecedor::find()->all(), 'id', 'nome'),
    ['prompt' => 'Selecione um fornecedor']
) ?>
    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
