<?php

namespace backend\models;

use Yii;

/**
 * This is the model class for table "fornecedor".
 *
 * @property int $id
 * @property string $nome
 * @property string $nif
 *
 * @property DescricaoCusto[] $descricaoCustos
 */
class Fornecedor extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'fornecedor';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['nome', 'nif'], 'required'],
            [['nome'], 'string', 'max' => 100],
            [['nif'], 'string', 'max' => 15],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'nome' => 'Nome',
            'nif' => 'Nif',
        ];
    }

    /**
     * Gets query for [[DescricaoCustos]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getDescricaoCustos()
    {
        return $this->hasMany(DescricaoCusto::class, ['fornecedor_id' => 'id']);
    }
}
