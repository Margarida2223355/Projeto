<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "inf_user".
 *
 * @property int $id
 * @property string $nome_completo
 * @property string $morada
 * @property string $pais
 * @property string $telefone
 * @property float $salario
 * @property string $nif
 *
 * @property User $id0
 */
class InfUser extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'inf_user';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['nome_completo', 'morada', 'pais', 'telefone', 'nif'], 'required'],
            [['salario'], 'number'],
            [['nome_completo'], 'string', 'max' => 150],
            [['morada'], 'string', 'max' => 100],
            [['pais'], 'string', 'max' => 50],
            [['telefone'], 'string', 'max' => 20],
            [['nif'], 'string', 'max' => 15],
            [['id'], 'exist', 'skipOnError' => true, 'targetClass' => User::class, 'targetAttribute' => ['id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'nome_completo' => 'Nome Completo',
            'morada' => 'Morada',
            'pais' => 'Pais',
            'telefone' => 'Telefone',
            'salario' => 'Salario',
            'nif' => 'Nif',
        ];
    }

    /**
     * Gets query for [[Id0]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getId0()
    {
        return $this->hasOne(User::class, ['id' => 'id']);
    }
    public function getRole(){
        $auth = Yii::$app->authManager;
        $roles = $auth->getRolesByUser($this->id);
        $roleUser = "null";
        foreach ($roles as $role) {
            //Type 1 = Roles, Type 2 = Permission (documentaçao do YII2)
            if($role->type == 1)
                $roleUser = $role->name;
        }
        return $roleUser;
    }
}
