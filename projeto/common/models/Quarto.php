<?php

namespace common\models;

use yii\helpers\ArrayHelper;
use common\models\Img;
use Yii;

/**
 * This is the model class for table "quarto".
 *
 * @property int $id
 * @property string $descricao
 * @property int $camas_solteiro
 * @property int $camas_casal
 * @property int $arcondicionado
 * @property int $aquecedor
 * @property float $preco
 *
 * @property Reserva[] $reservas
 */
class Quarto extends \yii\db\ActiveRecord
{

    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'quarto';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['descricao', 'camas_solteiro', 'camas_casal', 'arcondicionado', 'aquecedor', 'preco'], 'required'],
            [['camas_solteiro', 'camas_casal', 'arcondicionado', 'aquecedor'], 'integer'],
            [['preco'], 'number'],
            [['descricao'], 'string', 'max' => 50],
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
            'camas_solteiro' => 'Camas Solteiro',
            'camas_casal' => 'Camas Casal',
            'arcondicionado' => 'Arcondicionado',
            'aquecedor' => 'Aquecedor',
            'preco' => 'Preco',
        ];
    }

    /**
     * Gets query for [[Reservas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReservas()
    {
        return $this->hasMany(Reserva::class, ['quarto_id' => 'id']);
    }

    public function getImgs()
    {
        return $this->hasMany(Img::class, ['quarto_id' => 'id']);
    }

    public function getArcondicionado()
    {
        return $this->arcondicionado ? 'Disponivel' : 'Indisponivel';
    }

    public function getAquecedor()
    {
        return $this->aquecedor ? 'Disponivel' : 'Indisponivel';
    }

    public static function quartosDisponiveis($dataInicial, $dataFinal)
    {
        // Consulta para obter os IDs de quartos reservados entre as datas fornecidas
        $quartosReservados = Reserva::find()
            ->select('quarto_id')
            ->where(['between', 'data_inicial', $dataInicial, $dataFinal])
            ->orWhere(['between', 'data_final', $dataInicial, $dataFinal])
            ->asArray()
            ->column();

        // Consulta para obter todos os quartos que não estão na lista de quartos reservados
        $quartosDisponiveis = Quarto::find()
            ->where(['not in', 'id', $quartosReservados])
            ->all();

        // Formata os dados para o uso no dropDownList
        //$listaQuartos = ArrayHelper::map($quartosDisponiveis, 'id', 'descricao');

        return $quartosDisponiveis;
    }

}
