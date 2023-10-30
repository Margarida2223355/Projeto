<?php

use yii\db\Migration;

/**
 * Class m231030_205507_init_rbac
 */
class m231030_205507_init_rbac extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {

    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $auth = Yii::$app->authManager;
        $auth->removeAll();
    }

    /*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {
        echo "m231030_205507_init_rbac cannot be reverted.\n";

        return false;
    }
    */
}
