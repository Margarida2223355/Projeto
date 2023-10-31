<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "inf_cliente".
 *
 * @property int $id
 * @property string $nome_completo
 * @property string $telefone
 * @property string $morada
 * @property string $pais
 * @property string $nif
 * @property int $user_id
 *
 * @property User $user
 */
class InfCliente extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'inf_cliente';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['nome_completo', 'telefone', 'morada', 'pais', 'nif', 'user_id'], 'required'],
            [['user_id'], 'integer'],
            [['nome_completo', 'morada'], 'string', 'max' => 100],
            [['telefone'], 'string', 'max' => 20],
            [['pais'], 'string', 'max' => 50],
            [['nif'], 'string', 'max' => 15],
            [['user_id'], 'exist', 'skipOnError' => true, 'targetClass' => User::class, 'targetAttribute' => ['user_id' => 'id']],
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
            'telefone' => 'Telefone',
            'morada' => 'Morada',
            'pais' => 'Pais',
            'nif' => 'Nif',
            'user_id' => 'User ID',
        ];
    }

    /**
     * Gets query for [[User]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getUser()
    {
        return $this->hasOne(User::class, ['id' => 'user_id']);
    }
}
