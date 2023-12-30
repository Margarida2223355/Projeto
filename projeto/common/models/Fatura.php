<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "fatura".
 *
 * @property int $id
 * @property string|null $data_pagamento
 * @property float|null $preco_total
 * @property float|null $total_sem_imposto
 * @property int $reserva_id
 *
 * @property LinhaFaturaHasFatura[] $linhaFaturaHasFaturas
 * @property LinhaFatura[] $linhaFaturas
 * @property Reserva $reserva
 */
class Fatura extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'fatura';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['id', 'reserva_id'], 'required'],
            [['id', 'reserva_id'], 'integer'],
            [['data_pagamento'], 'safe'],
            [['preco_total', 'total_sem_imposto'], 'number'],
            [['id'], 'unique'],
            [['reserva_id'], 'exist', 'skipOnError' => true, 'targetClass' => Reserva::class, 'targetAttribute' => ['reserva_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'data_pagamento' => 'Data Pagamento',
            'preco_total' => 'Preco Total',
            'total_sem_imposto' => 'Total Sem Imposto',
            'reserva_id' => 'Reserva ID',
        ];
    }

    /**
     * Gets query for [[LinhaFaturaHasFaturas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getLinhaFaturaHasFaturas()
    {
        return $this->hasMany(LinhaFaturaHasFatura::class, ['fatura_id' => 'id']);
    }

    /**
     * Gets query for [[LinhaFaturas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getLinhaFaturas()
    {
        return $this->hasMany(LinhaFatura::class, ['id' => 'linha_fatura_id'])->viaTable('linha_fatura_has_fatura', ['fatura_id' => 'id']);
    }

    /**
     * Gets query for [[Reserva]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReserva()
    {
        return $this->hasOne(Reserva::class, ['id' => 'reserva_id']);
    }
}
