<?php

/** @var yii\web\View $this */
/** @var yii\bootstrap5\ActiveForm $form */
/** @var \common\models\LoginForm $model */

use yii\bootstrap5\ActiveForm;
use yii\bootstrap5\Html;

$this->title = 'Login';
?>
<?php
// Exibir popup se houver uma mensagem flash de erro
$errorMessage = Yii::$app->session->getFlash('error');
if ($errorMessage) {
    $js = <<<JS
    // Código JavaScript para exibir o popup (pode variar dependendo da biblioteca que você está usando)
    alert("$errorMessage");
JS;
    // Registrando o script no final da página
    $this->registerJs($js, \yii\web\View::POS_END);
}
?>
<div class="card text-center w-50 mx-auto">
    <div class="card-header center">
        <h1><?= Html::encode($this->title) ?></h1>
    </div>

    <div class="card-body">
        <div class="site-login">
            <div class="mt-5 offset-lg-3 col-lg-6">

                <?php $form = ActiveForm::begin(['id' => 'login-form']); ?>

                    <?= $form->field($model, 'username')->textInput(['autofocus' => true]) ?>

                    <?= $form->field($model, 'password')->passwordInput() ?>

                    <?= $form->field($model, 'rememberMe')->checkbox() ?>

                    <div class="form-group">
                        <?= Html::submitButton('Login', ['class' => 'btn btn-primary btn-block', 'name' => 'login-button']) ?>
                    </div>

                <?php ActiveForm::end(); ?>
            </div>
        </div>
    </div>
</div>
