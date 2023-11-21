<?php

use yii\helpers\Html;
use yii\bootstrap5\ActiveForm;
use yii\helpers\ArrayHelper;


/** @var yii\web\View $this */
/** @var common\models\infUser $model */

$this->title = 'Create Inf User';
$this->params['breadcrumbs'][] = ['label' => 'Inf Users', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="inf-user-create">

<h1><?= Html::encode($this->title) ?></h1>

<p>Please fill out the following fields to signup:</p>

<div class="row">
    <div class="col-lg-5">
        <?php $form = ActiveForm::begin(['id' => 'form-signup']); ?>

            <?= $form->field($model, 'username')->textInput(['autofocus' => true]) ?>

            <?= $form->field($model, 'email') ?>

            <?= $form->field($model, 'password')->passwordInput() ?>
            
            <?= $form->field($model, 'nome_completo') ?>
            <?= $form->field($model, 'morada') ?>
            <?= $form->field($model, 'pais') ?>
            <?= $form->field($model, 'telefone') ?>
            <?= $form->field($model, 'nif') ?>
            <?= $form->field($model, 'role')->dropDownList(ArrayHelper::map(Yii::$app->authManager->getRoles(), 'name', 'name'),['disabled' => !Yii::$app->user->can('gestor')]) ?>

            <div class="form-group">
                <?= Html::submitButton('Signup', ['class' => 'btn btn-primary', 'name' => 'signup-button']) ?>
            </div>

        <?php ActiveForm::end(); ?>
    </div>
</div>

</div>
