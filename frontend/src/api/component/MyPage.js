import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getUser = async () => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/api/user`,
        withCredentials: true,
    }

    return await axios(config);
};

export { getUser };