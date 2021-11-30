import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const insertReservationTimeRelation = async (id) => {

    const data = {
        reservationTimeId: id,
    }

    const config = {
        method: 'POST',
        url: `${backEndBaseUri}/api/reservation-time-relation`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

const deleteReservationTimeRelation = async (id) => {

    const config = {
        method: 'DELETE',
        url: `${backEndBaseUri}/api/reservation-time-relation/${id}`,
        withCredentials: true,
    }

    return await axios(config);
};


export { insertReservationTimeRelation, deleteReservationTimeRelation };