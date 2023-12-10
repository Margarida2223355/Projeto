<?php

use yii\helpers\Html;
use yii\widgets\DetailView;
use yii\widgets\ListView;
use yii\bootstrap4\Carousel;


/** @var yii\web\View $this */
/** @var common\models\Quarto $model */

$this->title = $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Quartos', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="quarto-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-danger',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'descricao',
            'camas_solteiro',
            'camas_casal',
            [
                'attribute' => 'arcondicionado',
                'value' => $model->getArcondicionado(),
            ],
            [
                'attribute' => 'aquecedor',
                'value' => $model->getAquecedor(),
            ],
            'preco',
        ],
    ]) ?>

    <h2>Imagens do Quarto</h2>

    <?php
    $items = [];
    foreach ($model->getImgs()->all() as $imagem) {
        $items[] = [
            'content' => Html::img('img/quartos/' . $imagem->image, ['class' => 'd-block w-100', 'alt' => 'Imagem do Quarto']),
        ];
    }
    echo Carousel::widget([
        'items' => $items,
        'options' => [
            'class' => 'carousel slide', // adiciona a classe do Bootstrap Carousel
            'data' => [
                'ride' => 'carousel',
            ],
        ],
        'controls' => [
            '<span class="carousel-control-prev-icon" aria-hidden="true"></span>',
            '<span class="carousel-control-next-icon" aria-hidden="true"></span>',
        ],
        'showIndicators' => true, // exibe indicadores de navegação
    ]);
    ?>

</div>