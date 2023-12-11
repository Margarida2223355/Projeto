<?php

use yii\helpers\Html;
use yii\bootstrap4\Carousel;

/** @var yii\web\View $this */

$this->title = 'Quartos';
$this->params['breadcrumbs'][] = $this->title;
?>

<div class="site-quartos">
    <div class="container">
        <h1 class="mb-4">Lista de Quartos</h1>

        <div class="row">
            <?php foreach ($quartos as $quarto): ?>
                <div class="col-lg-4 mb-4">
                    <div class="card h-100 shadow">
                        <?php
                        // Construa os dados do carrossel
                        $carouselItems = [];
                        foreach ($quarto->imgs as $imagem) {
                            $carouselItems[] = [
                                'content' => Html::img('@web/img/quartos/' . $imagem->image, ['class' => 'card-img-top img-fluid rounded', 'alt' => 'Imagem do Quarto']),
                            ];
                        }

                        // Exiba o carrossel
                        echo Carousel::widget([
                            'items' => $carouselItems,
                            'options' => [
                                'class' => 'carousel slide',
                                'data-interval' => 'false', // Adiciona esta linha para desativar a rotação automática
                            ],
                            'controls' => [
                                '<span class="carousel-control-prev-icon" aria-hidden="true"></span>',
                                '<span class="carousel-control-next-icon" aria-hidden="true"></span>',
                            ],
                        ]);
                        ?>

                        <div class="card-body">
                            <h5 class="card-title"><?= Html::encode($quarto->descricao) ?></h5>
                            <p class="card-text"><strong>Preço:</strong> <?= $quarto->preco ?></p>
                            <p class="card-text"><strong>Camas de casal:</strong> <?= $quarto->camas_casal ?></p>
                            <p class="card-text"><strong>Camas de solteiro:</strong> <?= $quarto->getArcondicionado() ?></p>
                            <p class="card-text"><strong>Ar condicionado:</strong> <?= $quarto->getAquecedor() ?></p>
                            <p class="card-text"><strong>Aquecedor:</strong> <?= $quarto->aquecedor ?></p>
                            
                            <?php 
                                $dataInicial = Yii::$app->request->get('dataInicial');
                                $dataFinal = Yii::$app->request->get('dataFinal');
                            ?>
                            <?= Html::a('Create Reserva', ['reserva/create', 'quarto_id' => $quarto->id, 'dataInicial' =>$dataInicial, 'dataFinal'=>$dataFinal], ['class' => 'btn btn-success']) ?>
                        </div>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>
    </div>
</div>