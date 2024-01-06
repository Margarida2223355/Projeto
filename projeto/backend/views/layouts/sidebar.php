<?php

use yii\helpers\Html;
?>
<aside class="main-sidebar sidebar-dark-primary elevation-4">

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="<?=$assetDir?>/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
            <?= Html::a(
                Yii::$app->user->identity->username,
                ['/inf-user/view', 'id' => Yii::$app->user->id],
                ['class' => 'd-block']
            ) ?>
            </div>
        </div>

        <!-- SidebarSearch Form -->
        <!-- href be escaped -->
        <!-- <div class="form-inline">
            <div class="input-group" data-widget="sidebar-search">
                <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
                <div class="input-group-append">
                    <button class="btn btn-sidebar">
                        <i class="fas fa-search fa-fw"></i>
                    </button>
                </div>
            </div>
        </div> -->

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <?php
            echo \hail812\adminlte\widgets\Menu::widget([
                'items' => [
                    ['label' => 'Quartos', 'icon' => 'bed', 'url' => ['/quarto/index']],
                    ['label' => 'Fornecedores', 'icon' => 'box', 'url' => ['/fornecedor/index'],'visible' => Yii::$app->user->can('gestor'),],
                    ['label' => 'Categoria Custos', 'icon' => 'chart-pie', 'url' => ['/descricao-custo/index'],'visible' => Yii::$app->user->can('gestor'),],
                    ['label' => 'Gastos', 'icon' => 'money-bill', 'url' => ['/custo/index'],'visible' => Yii::$app->user->can('gestor'),],
                    ['label' => 'Users', 'icon' => 'users', 'url' => ['/inf-user/index']],
                    ['label' => 'Serviços', 'icon' => 'hiking', 'url' => ['/servico/index']],
                    ['label' => 'Refeições', 'icon' => 'utensils', 'url' => ['/refeicao/index']],
                    ['label' => 'Pousada', 'icon' => 'hotel', 'url' => ['/pousada/index']],
                    ['label' => 'Reservas', 'icon' => 'book', 'url' => ['/reserva/index']],
                    ['label' => 'Pedidos', 'icon' => 'shopping-cart', 'url' => ['/linha-fatura/index']],
                    ['label' => 'Faturas', 'icon' => 'file-invoice', 'url' => ['/fatura/index']],
                ]   
            ]);
            ?>

        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>