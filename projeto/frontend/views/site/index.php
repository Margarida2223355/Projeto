<?php
use yii\helpers\Html;
use yii\bootstrap4\Carousel;

/** @var yii\web\View $this */

$this->title = 'My Yii Application';
?>
<div class="site-index">
    <div class="jumbotron p-5 mb-4 bg-light rounded-3 text-center">
        <div class="container">
            <h1 class="display-4">Pousada</h1>
        </div>
    </div>

    <div class="body-content ">
        <div class="site-index">
            <?php
            $carouselItems = [
                [
                    'content' => Html::img('img/pousada.jpeg', ['alt' => 'Pousada', 'class' => 'd-block w-100']),
                ],
                [
                    'content' => Html::img('img/5.jpeg', ['alt' => 'Quarto', 'class' => 'd-block w-100']),
                ],
                [
                    'content' => Html::img('img/pr18.jpeg', ['alt' => 'Quarto', 'class' => 'd-block w-100']),
                ],
            ];

            echo Carousel::widget([
                'items' => $carouselItems,
                'options' => [
                    'class' => 'carousel slide',
                    'data-ride' => 'carousel',
                ],
                'controls' => [
                    '<span class="carousel-control-prev-icon" aria-hidden="true"></span>',
                    '<span class="carousel-control-next-icon" aria-hidden="true"></span>',
                ],
            ]);
            ?>
        </div>
    </div>
</div>