<?php

/** @var yii\web\View $this */

use yii\helpers\Html;

$this->title = 'Quartos';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="site-quartos">
    <div class="container">
        <h1>Lista de Quartos</h1>

        <table class="table">
            <thead>
                <tr>
                    <th>Descrição</th>
                    <th>Preço</th>
                    <th>Disponibilidade</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($quartos as $quarto): ?>
                    <tr>
                        <td><?= $quarto->descricao ?></td>
                        <td><?= $quarto->preco ?></td>
                    </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
    </div>
</div>
