import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getReservation = async () => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/api/reservation`,
        withCredentials: true,
    }

    return await axios(config);
};

export { getReservation };