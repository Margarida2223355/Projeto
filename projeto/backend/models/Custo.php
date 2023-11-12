<?php

namespace backend\models;

use Yii;

/**
 * This is the model class for table "custo".
 *
 * @property int $id
 * @property int $descricao_custo_id
 * @property float $valor
 * @property string $data
 *
 * @property DescricaoCusto $descricaoCusto
 */
class Custo extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'custo';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['descricao_custo_id', 'valor', 'data'], 'required'],
            [['descricao_custo_id'], 'integer'],
            [['valor'], 'number'],
            [['data'], 'safe'],
            [['descricao_custo_id'], 'exist', 'skipOnError' => true, 'targetClass' => DescricaoCusto::class, 'targetAttribute' => ['descricao_custo_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'descricao_custo_id' => 'Descricao Custo ID',
            'valor' => 'Valor',
            'data' => 'Data',
        ];
    }

    /**
     * Gets query for [[DescricaoCusto]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getDescricaoCusto()
    {
        return $this->hasOne(DescricaoCusto::class, ['id' => 'descricao_custo_id']);
    }
}
