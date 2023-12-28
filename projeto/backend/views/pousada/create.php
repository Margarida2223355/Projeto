<?php

use yii\helpers\Html;

/** @var yii\web\View $this */
/** @var common\models\Pousada $model */

$this->title = 'Create Pousada';
$this->params['breadcrumbs'][] = ['label' => 'Pousadas', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="pousada-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
