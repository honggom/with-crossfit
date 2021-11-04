import { Cookies } from 'react-cookie';

export default function Cookie() {

    const cookies = new Cookies();

    const getCookie = (name) => {
        return cookies.get(name);
    }


    return (
      <div>
          <div>
          jwt {getCookie("jwt")}
          </div>

          <div>
          refresh {getCookie("refresh")}
          </div>
      </div>
    );
  }