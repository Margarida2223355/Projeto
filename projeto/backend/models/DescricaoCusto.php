<?php

namespace backend\models;

use Yii;

/**
 * This is the model class for table "descricao_custo".
 *
 * @property int $id
 * @property string $descricao
 * @property int $fornecedor_id
 *
 * @property Custo[] $custos
 * @property Fornecedor $fornecedor
 */
class DescricaoCusto extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'descricao_custo';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['descricao', 'fornecedor_id'], 'required'],
            [['fornecedor_id'], 'integer'],
            [['descricao'], 'string', 'max' => 100],
            [['fornecedor_id'], 'exist', 'skipOnError' => true, 'targetClass' => Fornecedor::class, 'targetAttribute' => ['fornecedor_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'descricao' => 'Descricao',
            'fornecedor_id' => 'Fornecedor ID',
        ];
    }

    /**
     * Gets query for [[Custos]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCustos()
    {
        return $this->hasMany(Custo::class, ['descricao_custo_id' => 'id']);
    }

    /**
     * Gets query for [[Fornecedor]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getFornecedor()
    {
        return $this->hasOne(Fornecedor::class, ['id' => 'fornecedor_id']);
    }
}
