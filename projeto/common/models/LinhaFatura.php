<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "linha_fatura".
 *
 * @property int $id
 * @property int $quantidade
 * @property int $servico_id
 * @property int $refeicao_id
 * @property float $sub_total
 * @property float $preco_unitario
 * @property int $reserva_id
 * @property string $status
 *
 * @property Refeicao $refeicao
 * @property Reserva $reserva
 * @property Servico $servico
 */
class LinhaFatura extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'linha_fatura';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['quantidade', 'servico_id', 'refeicao_id', 'sub_total', 'preco_unitario', 'reserva_id', 'status'], 'required'],
            [['quantidade', 'servico_id', 'refeicao_id', 'reserva_id'], 'integer'],
            [['sub_total', 'preco_unitario'], 'number'],
            [['status'], 'string'],
            [['refeicao_id'], 'exist', 'skipOnError' => true, 'targetClass' => Refeicao::class, 'targetAttribute' => ['refeicao_id' => 'id']],
            [['servico_id'], 'exist', 'skipOnError' => true, 'targetClass' => Servico::class, 'targetAttribute' => ['servico_id' => 'id']],
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
            'quantidade' => 'Quantidade',
            'servico_id' => 'Servico ID',
            'refeicao_id' => 'Refeicao ID',
            'sub_total' => 'Sub Total',
            'preco_unitario' => 'Preco Unitario',
            'reserva_id' => 'Reserva ID',
            'status' => 'Status',
        ];
    }

    /**
     * Gets query for [[Refeicao]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getRefeicao()
    {
        return $this->hasOne(Refeicao::class, ['id' => 'refeicao_id']);
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

    /**
     * Gets query for [[Servico]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getServico()
    {
        return $this->hasOne(Servico::class, ['id' => 'servico_id']);
    }
}
