<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\infUser $model */

$this->title = 'Create Inf User';
$this->params['breadcrumbs'][] = ['label' => 'Inf Users', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="inf-user-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
