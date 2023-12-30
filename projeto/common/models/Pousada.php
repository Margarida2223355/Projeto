<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "pousada".
 *
 * @property int $id
 * @property string $denominacao_social
 * @property string $morada
 * @property string $nif
 *
 * @property Fatura[] $faturas
 */
class Pousada extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'pousada';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['id', 'denominacao_social', 'morada', 'nif'], 'required'],
            [['id'], 'integer'],
            [['denominacao_social', 'morada'], 'string', 'max' => 250],
            [['nif'], 'string', 'max' => 15],
            [['id'], 'unique'],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'denominacao_social' => 'Denominacao Social',
            'morada' => 'Morada',
            'nif' => 'Nif',
        ];
    }

    /**
     * Gets query for [[Faturas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getFaturas()
    {
        return $this->hasMany(Fatura::class, ['pousada_id' => 'id']);
    }
}
